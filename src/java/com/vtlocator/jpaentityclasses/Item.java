/*
 * Created by VTLocator Group on 2016.04.04  *
 * Copyright © 2016 VTLocator Group. All rights reserved. *
 */
package com.vtlocator.jpaentityclasses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @purpose The Item.java class creates a definition for an Item.
 * @author VTLocator Group
 */
@Entity
@Table(name = "Item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "Item.findByName", query = "SELECT i FROM Item i WHERE i.name = :name"),
    @NamedQuery(name = "Item.findByDescription", query = "SELECT i FROM Item i WHERE i.description = :description"),
    @NamedQuery(name = "Item.findByLatitudeFound", query = "SELECT i FROM Item i WHERE i.latitudeFound = :latitudeFound"),
    @NamedQuery(name = "Item.findByLongitudeFound", query = "SELECT i FROM Item i WHERE i.longitudeFound = :longitudeFound"),
    @NamedQuery(name = "Item.findByDateFound", query = "SELECT i FROM Item i WHERE i.dateFound = :dateFound"),
    @NamedQuery(name = "Item.findByCategory", query = "SELECT i FROM Item i WHERE i.category = :category"),
    @NamedQuery(name = "Item.findByCreatedAt", query = "SELECT i FROM Item i WHERE i.createdAt = :createdAt"),
    @NamedQuery(name = "Item.findByUpdatedAt", query = "SELECT i FROM Item i WHERE i.updatedAt = :updatedAt"),
    @NamedQuery(name = "Item.findByUserCreatorOrdered", query = "SELECT i FROM Item i WHERE i.createdBy.id = :createdBy ORDER BY i.createdAt desc")})
public class Item implements Serializable, Comparable<Item> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Size(max = 1024)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude_found")
    private BigDecimal latitudeFound;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude_found")
    private BigDecimal longitudeFound;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_found")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFound;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "category")
    private String category;
    @Basic(optional = true)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User createdBy;
    @OneToMany(mappedBy = "itemId")
    private Collection<ItemPhoto> itemPhotoCollection;

    @Transient
    private double distanceFromLocation;

    @Transient
    private String formattedDistance;

    public String getFormattedDistance() {
        return formattedDistance;
    }

    public void setFormattedDistance(String formattedDistance) {
        this.formattedDistance = formattedDistance;
    }

    public double getDistanceFromLocation() {
        return distanceFromLocation;
    }

    public void setDistanceFromLocation(double distanceFromLocation) {
        this.distanceFromLocation = distanceFromLocation;
    }

    // formats the date into a specific format.
    public String getFancyDate() {
         Date date = this.getDateFound(); // your date
         SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd yyyy");
         return format.format(date);
     }

     public Item() {
     }

     public Item(Integer id) {
        this.id = id;
    }

    /**
     * Constructor for Item
     * @param  id             [item ID]
     * @param  name           [item name]
     * @param  latitudeFound  [latitude of item]
     * @param  longitudeFound [longitude of item]
     * @param  dateFound      [item date found]
     * @param  category       [item category]
     * @param  createdAt      [when the item was created]
     * @param  updatedAt      [when the item was updated]
     * @return                [an item]
     */
    public Item(Integer id, String name, BigDecimal latitudeFound, BigDecimal longitudeFound, Date dateFound, String category, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.latitudeFound = latitudeFound;
        this.longitudeFound = longitudeFound;
        this.dateFound = dateFound;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getLatitudeFound() {
        return latitudeFound;
    }

    public void setLatitudeFound(BigDecimal latitudeFound) {
        this.latitudeFound = latitudeFound;
    }

    public BigDecimal getLongitudeFound() {
        return longitudeFound;
    }

    public void setLongitudeFound(BigDecimal longitudeFound) {
        this.longitudeFound = longitudeFound;
    }

    public Date getDateFound() {
        return dateFound;
    }

    public void setDateFound(Date dateFound) {
        this.dateFound = dateFound;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @XmlTransient
    public Collection<ItemPhoto> getItemPhotoCollection() {
        return itemPhotoCollection;
    }

    public void setItemPhotoCollection(Collection<ItemPhoto> itemPhotoCollection) {
        this.itemPhotoCollection = itemPhotoCollection;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpaentityclasses.Item[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Item o) {
        if (o.getDistanceFromLocation() < this.getDistanceFromLocation()) {
            return 1;
        } else if (o.getDistanceFromLocation() == this.getDistanceFromLocation()) {
            return 0;
        } else {
            return -1;
        }
    }
}
