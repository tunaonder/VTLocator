/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.jpaentityclasses;

import com.vtlocator.managers.Constants;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Onder
 */
@Entity
@Table(name = "UserPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPhoto.findAll", query = "SELECT u FROM UserPhoto u"),
    @NamedQuery(name = "UserPhoto.findById", query = "SELECT u FROM UserPhoto u WHERE u.id = :id"),
    @NamedQuery(name = "UserPhoto.findPhotosByUserId", query = "SELECT u FROM UserPhoto u WHERE u.userId.id = :userId"),
    @NamedQuery(name = "UserPhoto.findByExtension", query = "SELECT u FROM UserPhoto u WHERE u.extension = :extension"),
    @NamedQuery(name = "UserPhoto.findByCreatedAt", query = "SELECT u FROM UserPhoto u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "UserPhoto.findByUpdatedAt", query = "SELECT u FROM UserPhoto u WHERE u.updatedAt = :updatedAt")})
public class UserPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "extension")
    private String extension;
    @Basic(optional = true)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;
    
    public UserPhoto() {
    }

    public UserPhoto(Integer id) {
        this.id = id;
    }
    
    public UserPhoto( String extension, User user) {
        this.extension = extension;
        this.userId = user;
    }

    public UserPhoto(Integer id, String extension, Date createdAt, Date updatedAt) {
        this.id = id;
        this.extension = extension;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserPhoto)) {
            return false;
        }
        UserPhoto other = (UserPhoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpaentityclasses.UserPhoto[ id=" + id + " ]";
    }
    
    // Added methods:
    public String getFilePath() {
        return Constants.ROOT_DIRECTORY + getFilename();
    }

    public String getFilename() {
        return getId() + "." + getExtension();
    }
    
}
