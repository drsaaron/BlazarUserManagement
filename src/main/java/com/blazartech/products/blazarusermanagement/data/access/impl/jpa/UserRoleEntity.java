/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data.access.impl.jpa;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AAR1069
 */
@Entity
@Table(name = "USER_ROLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRoleEntity.findAll", query = "SELECT u FROM UserRoleEntity u"),
    @NamedQuery(name = "UserRoleEntity.findByUserRoleNum", query = "SELECT u FROM UserRoleEntity u WHERE u.userRoleNum = :userRoleNum"),
    @NamedQuery(name = "UserRoleEntity.findByUserNum", query = "SELECT u FROM UserRoleEntity u WHERE u.userNum.userNum = :userNum"),
})
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ROLE_NUM")
    private Long userRoleNum;
    @JoinColumn(name = "USER_NUM", referencedColumnName = "USER_NUM")
    @ManyToOne(optional = false)
    private UserDataEntity userNum;
    @JoinColumn(name = "USER_ROLE_TYPE_CDE", referencedColumnName = "USER_ROLE_TYPE_CDE")
    @ManyToOne(optional = false)
    private UserRoleTypeValEntity userRoleTypeCde;

    public UserRoleEntity() {
    }

    public UserRoleEntity(Long userRoleNum) {
        this.userRoleNum = userRoleNum;
    }
    
    public UserRoleEntity(Long userRoleNum, UserRoleTypeValEntity userRoleTypeCde) {
        this.userRoleNum = userRoleNum;
        this.userRoleTypeCde = userRoleTypeCde;
    }

    public Long getUserRoleNum() {
        return userRoleNum;
    }

    public void setUserRoleNum(Long userRoleNum) {
        this.userRoleNum = userRoleNum;
    }

    public UserDataEntity getUserNum() {
        return userNum;
    }

    public void setUserNum(UserDataEntity userNum) {
        this.userNum = userNum;
    }

    public UserRoleTypeValEntity getUserRoleTypeCde() {
        return userRoleTypeCde;
    }

    public void setUserRoleTypeCde(UserRoleTypeValEntity userRoleTypeCde) {
        this.userRoleTypeCde = userRoleTypeCde;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleNum != null ? userRoleNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRoleEntity)) {
            return false;
        }
        UserRoleEntity other = (UserRoleEntity) object;
        if ((this.userRoleNum == null && other.userRoleNum != null) || (this.userRoleNum != null && !this.userRoleNum.equals(other.userRoleNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleEntity[ userRoleNum=" + userRoleNum + " ]";
    }
    
}
