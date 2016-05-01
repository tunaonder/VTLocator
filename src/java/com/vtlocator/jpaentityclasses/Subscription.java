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
@Table(name = "Subscription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subscription.findAll", query = "SELECT s FROM Subscription s"),
    @NamedQuery(name = "Subscription.findById", query = "SELECT s FROM Subscription s WHERE s.id = :id"),
    @NamedQuery(name = "Subscription.findByCategory", query = "SELECT s FROM Subscription s WHERE s.category = :category"),
    @NamedQuery(name = "Subscription.findByCreatedAt", query = "SELECT s FROM Subscription s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Subscription.findByUpdatedAt", query = "SELECT s FROM Subscription s WHERE s.updatedAt = :updatedAt")})
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "subscriber", referencedColumnName = "id")
    @ManyToOne
    private User subscriber;

    /**
     * Default empty constructor
     */
    public Subscription() {
    }

    /**
     * Default constructor for with id as parameter
     * @param id the id for the subscription
     */
    public Subscription(Integer id) {
        this.id = id;
    }

    /**
     * Constructor that takes in all fields to set
     * @param id the id of the subscription
     * @param category the category for the subscription
     * @param createdAt the date the subscription was created
     * @param updatedAt the date the subscription was last updated
     */
    public Subscription(Integer id, String category, Date createdAt, Date updatedAt) {
        this.id = id;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    /**
     * Gets the id of the subscription
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the subscription
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the category of the subscription
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the subscription
     * @param category the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the date the subscription was created at
     * @return the date of creation
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date the subscription was created
     * @param createdAt the date it was created
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the date the subscription was last updated
     * @return  last updated date
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last update date for the subscription
     * @param updatedAt the date it was last updated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the user associated with the subscription
     * @return user for the subscription
     */
    public User getSubscriber() {
        return subscriber;
    }

    /**
     * Sets the user who is subscribed
     * @param subscriber user who is subscribed
     */
    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
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
        if (!(object instanceof Subscription)) {
            return false;
        }
        Subscription other = (Subscription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpaentityclasses.Subscription[ id=" + id + " ]";
    }
    
}
