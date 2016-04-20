/*
 * Created by Sait Tuna Onder on 2016.04.04  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.vtlocator.jpaentityclasses;

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
@Table(name = "ItemPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemPhoto.findAll", query = "SELECT i FROM ItemPhoto i"),
    @NamedQuery(name = "ItemPhoto.findById", query = "SELECT i FROM ItemPhoto i WHERE i.id = :id"),
    @NamedQuery(name = "ItemPhoto.findByExtension", query = "SELECT i FROM ItemPhoto i WHERE i.extension = :extension"),
    @NamedQuery(name = "ItemPhoto.findByCreatedAt", query = "SELECT i FROM ItemPhoto i WHERE i.createdAt = :createdAt"),
    @NamedQuery(name = "ItemPhoto.findByUpdatedAt", query = "SELECT i FROM ItemPhoto i WHERE i.updatedAt = :updatedAt")})
public class ItemPhoto implements Serializable {

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
    @JoinColumn(name = "photo_for", referencedColumnName = "id")
    @ManyToOne
    private Item photoFor;
    
    public ItemPhoto() {
    }

    public ItemPhoto(Integer id) {
        this.id = id;
    }

    public ItemPhoto(Integer id, String extension, Date createdAt, Date updatedAt) {
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

    public Item getPhotoFor() {
        return photoFor;
    }

    public void setPhotoFor(Item photoFor) {
        this.photoFor = photoFor;
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
        if (!(object instanceof ItemPhoto)) {
            return false;
        }
        ItemPhoto other = (ItemPhoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpaentityclasses.ItemPhoto[ id=" + id + " ]";
    }
    
}
