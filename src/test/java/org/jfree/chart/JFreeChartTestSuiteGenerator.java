package org.jfree.chart;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JFreeChartTestSuiteGenerator {

    public static void main(String[] args) {

        Class<?> testClass = AreaChartTest.class; // Replace with your test class

        List<Method> testMethods = getTestMethods(testClass);

        List<Method> selectedMethods = selectRandomMethods(testMethods, 10); // Example: select 10 methods randomly

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()

                .selectors(selectedMethods.stream()

                        .map(method -> DiscoverySelectors.selectMethod(testClass, method.getName()))

                        .collect(Collectors.toList()))

                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        // Output the results

        System.out.println(listener.getSummary().toString());

    }

    private static List<Method> getTestMethods(Class<?> testClass) {

        List<Method> testMethods = new ArrayList<>();

        for (Method method : testClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(Test.class)) {

                testMethods.add(method);

            }

        }

        return testMethods;

    }

    private static List<Method> selectRandomMethods(List<Method> methods, int count) {

        Collections.shuffle(methods);

        if (methods.size() < count) {

            return methods;

        } else {

            return methods.subList(0, count);

        }

    }

}
