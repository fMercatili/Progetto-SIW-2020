package it.uniroma3.siw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Project is an activity managed by the TaskManager.
 * It is generated and owned by a specific User, that can grant visibility over it to multiple other ones.
 * It can contain one or multiple individual Tasks.
 */
@Entity
public class Project {

    /**
     * Unique identifier for this Project
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Name for this Project
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Description for this Project
     */
    @Column
    private String description;

    /**
     * Name for this Project
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    /**
     * Name for this Project
     */
    @ManyToMany(fetch = FetchType.LAZY)                                // fetch is LAZY by default
    private List<User> members;

    /**
     * Tasks that this project contains
     */
    @OneToMany(       // whenever a Project is retrieved, always retrieve its tasks too
    		cascade = CascadeType.REMOVE, mappedBy="project")   // if a Project is deleted, all its tasks must be deleted too
    private List<Task> tasks;


    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Tag> tagList;

    public Project() {
        this.members = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public Project(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public void addMember(User user) {
        if (!this.members.contains(user))
            this.members.add(user);
    }


    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {

        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    // this is a semplification
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
