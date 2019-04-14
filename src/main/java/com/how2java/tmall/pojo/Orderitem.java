package com.how2java.tmall.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Created by chen
 * @date 2019/3/20 10:32
 */
@Entity
public class Orderitem {
    private int id;
    private Integer pid;
    private Integer oid;
    private Integer uid;
    private Integer number;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "oid")
    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "uid")
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orderitem orderitem = (Orderitem) o;
        return id == orderitem.id &&
                Objects.equals(pid, orderitem.pid) &&
                Objects.equals(oid, orderitem.oid) &&
                Objects.equals(uid, orderitem.uid) &&
                Objects.equals(number, orderitem.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pid, oid, uid, number);
    }
}
