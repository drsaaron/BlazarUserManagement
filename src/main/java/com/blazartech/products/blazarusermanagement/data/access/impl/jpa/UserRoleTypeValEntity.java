/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data.access.impl.jpa;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AAR1069
 */
@Entity
@Table(name = "USER_ROLE_TYPE_VAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRoleTypeValEntity.findAll", query = "SELECT u FROM UserRoleTypeValEntity u"),
    @NamedQuery(name = "UserRoleTypeValEntity.findByUserRoleTypeCde", query = "SELECT u FROM UserRoleTypeValEntity u WHERE u.userRoleTypeCde = :userRoleTypeCde"),
    @NamedQuery(name = "UserRoleTypeValEntity.findByUserRoleTypeTxt", query = "SELECT u FROM UserRoleTypeValEntity u WHERE u.userRoleTypeTxt = :userRoleTypeTxt")})
public class UserRoleTypeValEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ROLE_TYPE_CDE")
    private Short userRoleTypeCde;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "USER_ROLE_TYPE_TXT")
    private String userRoleTypeTxt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRoleTypeCde")
    private Collection<UserRoleEntity> userRoleEntityCollection;

    public UserRoleTypeValEntity() {
    }

    public UserRoleTypeValEntity(Short userRoleTypeCde) {
        this.userRoleTypeCde = userRoleTypeCde;
    }

    public UserRoleTypeValEntity(Short userRoleTypeCde, String userRoleTypeTxt) {
        this.userRoleTypeCde = userRoleTypeCde;
        this.userRoleTypeTxt = userRoleTypeTxt;
    }

    public Short getUserRoleTypeCde() {
        return userRoleTypeCde;
    }

    public void setUserRoleTypeCde(Short userRoleTypeCde) {
        this.userRoleTypeCde = userRoleTypeCde;
    }

    public String getUserRoleTypeTxt() {
        return userRoleTypeTxt;
    }

    public void setUserRoleTypeTxt(String userRoleTypeTxt) {
        this.userRoleTypeTxt = userRoleTypeTxt;
    }

    @XmlTransient
    public Collection<UserRoleEntity> getUserRoleEntityCollection() {
        return userRoleEntityCollection;
    }

    public void setUserRoleEntityCollection(Collection<UserRoleEntity> userRoleEntityCollection) {
        this.userRoleEntityCollection = userRoleEntityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleTypeCde != null ? userRoleTypeCde.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleTypeValEntity)) {
            return false;
        }
        UserRoleTypeValEntity other = (UserRoleTypeValEntity) object;
        if ((this.userRoleTypeCde == null && other.userRoleTypeCde != null) || (this.userRoleTypeCde != null && !this.userRoleTypeCde.equals(other.userRoleTypeCde))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleTypeValEntity[ userRoleTypeCde=" + userRoleTypeCde + " ]";
    }
    
}
