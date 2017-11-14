package com.capgemini.ntc.test.core.utils.junitoolboxpi;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.junit.runner.manipulation.*;
import org.junit.runners.Suite;
import org.junit.runners.model.*;

public class WildcardPatternSuiteBF extends Suite {
	private static String javaPackageToSearch = null;

	static {
		Properties prop = new Properties();
		String fileName = "settings.properties";
		InputStream is;
		try {
			is = new FileInputStream(fileName);
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		javaPackageToSearch = prop.getProperty("javaPackageToSearch");
	}

	private static Class<?>[] getSuiteClasses(Class<?> klass) throws InitializationError {
		final org.junit.runners.Suite.SuiteClasses annotation1 = klass
				.getAnnotation(org.junit.runners.Suite.SuiteClasses.class);
		final com.googlecode.junittoolbox.SuiteClasses annotation2 = klass
				.getAnnotation(com.googlecode.junittoolbox.SuiteClasses.class);
		if (annotation1 == null && annotation2 == null) {
			throw new InitializationError("class " + klass.getName() + " must have a SuiteClasses annotation");
		}
		final Class<?>[] suiteClasses1 = (annotation1 == null ? null : annotation1.value());
		final Class<?>[] suiteClasses2 = (annotation2 == null ? null : findSuiteClasses(klass, annotation2.value()));
		return union(suiteClasses1, suiteClasses2);
	}

	private static Class<?>[] findSuiteClasses(Class<?> klass, String... wildcardPatterns) throws InitializationError {
		final File baseDir = getBaseDirPi();
		try {
			final String basePath = baseDir.getCanonicalPath().replace('\\', '/');
			final List<Pattern> includePatterns = new ArrayList<Pattern>();
			final List<Pattern> excludePatterns = new ArrayList<Pattern>();
			for (String wildcardPattern : wildcardPatterns) {
				if (wildcardPattern == null) {
					throw new InitializationError("wildcard pattern for the SuiteClasses annotation must not be null");
				}
				boolean exclude = wildcardPattern.startsWith("!");
				if (exclude) {
					wildcardPattern = wildcardPattern.substring(1);
				}
				if (wildcardPattern.startsWith("/")) {
					throw new InitializationError(
							"wildcard pattern for the SuiteClasses annotation must not start with a '/' character");
				}
				if (!exclude && !wildcardPattern.endsWith(".class")) {
					throw new InitializationError(
							"wildcard pattern for the SuiteClasses annotation must end with \".class\"");
				}
				Pattern regex = convertWildcardPatternToRegex("/" + wildcardPattern);
				(exclude ? excludePatterns : includePatterns).add(regex);
			}
			final IOFileFilter fileFilter = new AbstractFileFilter() {
				@Override
				public boolean accept(File file) {
					try {
						// Never accept directories, hidden files, and inner classes ...
						if (file.isDirectory() || file.isHidden() || file.getName().contains("$")) {
							return false;
						}
						final String canonicalPath = file.getCanonicalPath().replace('\\', '/');
						if (canonicalPath.startsWith(basePath)) {
							final String path = canonicalPath.substring(basePath.length());
							for (Pattern excludePattern : excludePatterns) {
								if (excludePattern.matcher(path).matches()) {
									return false;
								}
							}
							for (Pattern includePattern : includePatterns) {
								if (includePattern.matcher(path).matches()) {
									return true;
								}
							}
							return false;
						} else {
							return false;
						}
					} catch (IOException e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			};
			final Collection<File> classFiles = FileUtils.listFiles(baseDir, fileFilter, TrueFileFilter.INSTANCE);
			if (classFiles.isEmpty()) {
				throw new InitializationError(
						"did not find any *.class file using the specified wildcard patterns "
								+ Arrays.toString(wildcardPatterns) + " in directory " + basePath);
			}
			final String classNamePrefix = javaPackageToSearch + ".";
			final Class<?>[] result = new Class<?>[classFiles.size()];
			int i = 0;
			final ClassLoader classLoader = klass.getClassLoader();
			for (File file : classFiles) {
				final String canonicalPath = file.getCanonicalPath().replace('\\', '/');
				assert canonicalPath.startsWith(basePath) && canonicalPath.endsWith(".class");
				final String path = canonicalPath.substring(basePath.length() + 1);
				final String className = classNamePrefix
						+ path.substring(0, path.length() - ".class".length()).replace('/', '.');
				result[i++] = classLoader.loadClass(className);
			}
			return result;
		} catch (Exception e) {
			throw new InitializationError(
					"failed to scan " + baseDir + " using wildcard patterns " + Arrays.toString(wildcardPatterns)
							+ " -- " + e);
		}
	}

	private static File getBaseDirPi() throws InitializationError {
		File f = null;
		try {
			String javaPackageInProjectForSearchTests = javaPackageToSearch.replace('.', '/');
			String folderWithTestCases = System.getProperty("user.dir") + "/target/test-classes/";
			String completedPathToPackageToSearchTests = folderWithTestCases + javaPackageInProjectForSearchTests;
			f = new File(completedPathToPackageToSearchTests);
			return f;
		} catch (Exception e) {
			throw new InitializationError(
					"failed to determine directory of " + f.getAbsolutePath() + ",  " + e.getMessage());
		}
	}

	private static Pattern convertWildcardPatternToRegex(String wildCardPattern) throws InitializationError {
		String s = wildCardPattern;
		while (s.contains("***")) {
			s = s.replace("***", "**");
		}
		String suffix;
		if (s.endsWith("/**")) {
			s = s.substring(0, s.length() - 3);
			suffix = "(.*)";
		} else {
			suffix = "";
		}
		s = s.replace(".", "[.]");
		s = s.replace("/**/", "/::/");
		s = s.replace("*", "([^/]*)");
		s = s.replace("/::/", "((/.*/)|(/))");
		s = s.replace("?", ".");
		if (s.contains("**")) {
			throw new InitializationError("Invalid wildcard pattern \"" + wildCardPattern + "\"");
		}
		return Pattern.compile(s + suffix);
	}

	private static Class<?>[] union(Class<?>[] suiteClasses1, Class<?>[] suiteClasses2) {
		if (suiteClasses1 == null) {
			return suiteClasses2;
		} else if (suiteClasses2 == null) {
			return suiteClasses1;
		} else {
			final HashSet<Class<?>> temp = new HashSet<Class<?>>();
			temp.addAll(Arrays.asList(suiteClasses1));
			temp.addAll(Arrays.asList(suiteClasses2));
			final Class<?>[] result = new Class<?>[temp.size()];
			temp.toArray(result);
			return result;
		}
	}

	public WildcardPatternSuiteBF(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(builder, klass, getSuiteClasses(klass));
		Filter filter = CategoriesFilterBF.forTestSuite(klass);
		if (filter != null) {
			try {
				filter(filter);
			} catch (NoTestsRemainException e) {
				throw new InitializationError(e);
			}
		}
	}
}
