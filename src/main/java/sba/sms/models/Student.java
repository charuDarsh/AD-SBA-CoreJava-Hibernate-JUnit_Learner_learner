package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = {"collections"})

public class Student {

    @Id
    @NonNull
    @Column(name = "Email", length = 50, nullable = false)
    private String email;

    @NonNull
    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @NonNull
    @Column(name = "Password", length = 50, nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="student_courses",
            joinColumns = @JoinColumn(name="student_email",referencedColumnName ="email" ),
            inverseJoinColumns = @JoinColumn(name="courses_id",referencedColumnName = "id"))
    private Set<Course> courses;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student that = (Student) o;
        return email == that.email && Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password);
    }
}









