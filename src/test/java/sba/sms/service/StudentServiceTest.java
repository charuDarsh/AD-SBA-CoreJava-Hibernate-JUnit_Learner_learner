package sba.sms.service;

import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.services.CourseService;
import sba.sms.services.StudentService;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;



public class StudentServiceTest {


    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentI studentDao;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        // This can also be done in the @BeforeEach setup method if you prefer
        studentService = new StudentService();
    }
    @AfterEach
    public void tearDown() {
        HibernateUtil.shutdown();
    }
    @Test
    public void testCreateStudent() {
        Student student = new Student("test@gmail.com", "Test User", "password");
        studentService.createStudent(student);

        Student createdStudent = studentService.getStudentByEmail("test@gmail.com");
        assertNotNull(createdStudent);
        assertEquals("test@gmail.com", createdStudent.getEmail());
        assertEquals("Test User", createdStudent.getName());
    }

    @Test
    public void testGetStudentByEmail() {
        Student student = new Student("example@gmail.com", "Example User", "password");
        studentService.createStudent(student);

        Student retrievedStudent = studentService.getStudentByEmail("example@gmail.com");
        assertNotNull(retrievedStudent);
        assertEquals("example@gmail.com", retrievedStudent.getEmail());
        assertEquals("Example User", retrievedStudent.getName());
    }


    @Test
    public void testValidateStudent() {
        Student student = new Student("valid@gmail.com", "Valid User", "password");
        studentService.createStudent(student);

        boolean isValid = studentService.validateStudent("valid@gmail.com", "password");
        assertTrue(isValid);

        boolean isInvalid = studentService.validateStudent("valid@gmail.com", "wrongpassword");
        assertFalse(isInvalid);
    }
    @Test
    public void testRegisterStudentToCourse() {
        // Prepare students and courses
        Student student = new Student("student@gmail.com", "Student", "password");
        Course course = new Course("Java", "Instructor");

        studentService.createStudent(student);
        CourseService courseService = new CourseService();
        courseService.createCourse(course);

        studentService.registerStudentToCourse("student@gmail.com", course.getId());
        List<Course> studentCourses = studentService.getStudentCourses("student@gmail.com");

        assertTrue(studentCourses.contains(course));
    }
    @Test
    public void testGetStudentCourses() {
        // Prepare students and courses
        Student student = new Student("student2@gmail.com", "Student2", "password");
        Course course1 = new Course("Java", "Instructor1");
        Course course2 = new Course("Python", "Instructor2");

        studentService.createStudent(student);
        CourseService courseService = new CourseService();
        courseService.createCourse(course1);
        courseService.createCourse(course2);

        studentService.registerStudentToCourse("student2@gmail.com", course1.getId());
        studentService.registerStudentToCourse("student2@gmail.com", course2.getId());

        List<Course> studentCourses = studentService.getStudentCourses("student2@gmail.com");

        assertTrue(studentCourses.contains(course1));
        assertTrue(studentCourses.contains(course2));
    }
}




