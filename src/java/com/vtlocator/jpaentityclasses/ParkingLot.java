/*
 * Created by VTLocator Group on 2016.04.04  *
 * Copyright © 2016 VTLocator Group. All rights reserved. *
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @purpose The ParkingLot.java class defines a parkinglot with long,lat and name, permission.
 * @author VTLocator Group
 */
@Entity
@Table(name = "ParkingLot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParkingLot.findAll", query = "SELECT p FROM ParkingLot p"),
    @NamedQuery(name = "ParkingLot.findById", query = "SELECT p FROM ParkingLot p WHERE p.id = :id"),
    @NamedQuery(name = "ParkingLot.findByName", query = "SELECT p FROM ParkingLot p WHERE p.name = :name"),
    @NamedQuery(name = "ParkingLot.findByLatitude", query = "SELECT p FROM ParkingLot p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "ParkingLot.findByLongitude", query = "SELECT p FROM ParkingLot p WHERE p.longitude = :longitude"),
    @NamedQuery(name = "ParkingLot.findByPermission", query = "SELECT p FROM ParkingLot p WHERE p.permission = :permission"),
    @NamedQuery(name = "ParkingLot.findByCreatedAt", query = "SELECT p FROM ParkingLot p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "ParkingLot.findByUpdatedAt", query = "SELECT p FROM ParkingLot p WHERE p.updatedAt = :updatedAt")})
public class ParkingLot implements Serializable {

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private String longitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "permission")
    private String permission;

    @Basic(optional = true)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public ParkingLot() {
    }

    public ParkingLot(Integer id) {
        this.id = id;
    }

    /**
     * Parking Lot is a java object that describes a parking lot on a map. It does not contain the polygon, but the long/lat of the center of the parking lot.
     * @param  id         [id of parking lot]
     * @param  name       [parking lot name]
     * @param  latitude   [parking lot latitude]
     * @param  longitude  [parking lot longitude]
     * @param  permission [parking lot permission: ANY, COMMUTER, FACULTY, RESIDENT, METERED, PARKING_OFFICE]
     * @param  createdAt  [parking lot created date]
     * @param  updatedAt  [parking lot updated date]
     * @return            [new parking lot]
     */
    public ParkingLot(Integer id, String name, String latitude, String longitude, String permission, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.permission = permission;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParkingLot)) {
            return false;
        }
        ParkingLot other = (ParkingLot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpaentityclasses.ParkingLot[ id=" + id + " ]";
    }

}
