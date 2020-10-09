package ar.com.alkemylab.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users") // Se mapea con la tabla users de la BD
public class Users {

    // Atributos
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String username;
    private String password;
    @Column(name = "user_type")
    private Integer userTypeId;

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "admin_id")
    private Admin admin;
    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;
     //------------------Empieza enum----------------------------
  
     public enum UserTypeEnum {
        ADMIN(0),STUDENT(1);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private UserTypeEnum(Integer value) {
            this.value = value;

        }

        public Integer getValue() {
            return value;
        }

        public static UserTypeEnum parse(Integer id) {
            UserTypeEnum status = null; // Default
            for (UserTypeEnum item : UserTypeEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    // Getters y Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
       
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeEnum getUserTypeId() {
        return UserTypeEnum.parse(this.userTypeId);
    }

    public void setUserTypeId(UserTypeEnum userTypeId) {
        this.userTypeId = userTypeId.getValue();
    }

    public Integer getEntityId() {
        //segun el tipo de usuario, devolver el docenteId o estudianteId o nada!
    
        switch (this.getUserTypeId()) {
            case ADMIN:
                return this.getAdmin().getId();
            case STUDENT:
                return this.getStudent().getId();
    
            default:
                break;
        }
        return null;
    }

}
