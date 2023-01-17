package ivans.start.messager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "Names")
public class Name {
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private boolean is_existed;

    public Name(String name, boolean is_existed) {
        this.name = name;
        this.is_existed = is_existed;
    }

    public Name() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_existed() {
        return is_existed;
    }

    public void setIs_existed(boolean is_existed) {
        this.is_existed = is_existed;
    }
}
