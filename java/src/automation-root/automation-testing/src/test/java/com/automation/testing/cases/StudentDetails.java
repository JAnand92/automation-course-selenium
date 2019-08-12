package com.automation.testing.cases;

import com.automation.api.components.Student;
import com.automation.api.pages.Students;
import com.automation.core.components.FluentUi;
import com.automation.core.testing.TestCase;
import com.automation.framework.ui.pages.StudentsUi;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Map;

public class StudentDetails extends TestCase {
    @Override
    public boolean automationTest(Map<String, Object> testParams)
            throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        // create driver for this test case
        String application = testParams.get("application").toString();
        String keyword = testParams.get("keyword").toString();

        Student student = new  FluentUi(getDriver())
                .<Students>changeContext(StudentsUi.class, application)
                .findByName(keyword)
                .students()
                .stream()
                .findFirst()
                .orElse(null);

        // extract expected result
        assert student != null;
        String expected = student.firstName();

        // assert
        return student.details().firstName().equals(expected);
    }
}