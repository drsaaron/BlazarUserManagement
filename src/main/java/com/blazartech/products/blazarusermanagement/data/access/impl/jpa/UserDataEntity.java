/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data.access.impl.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AAR1069
 */
@Entity
@Table(name = "USER_INFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserDataEntity.findAll", query = "SELECT u FROM UserDataEntity u"),
    @NamedQuery(name = "UserDataEntity.findByUserNum", query = "SELECT u FROM UserDataEntity u WHERE u.userNum = :userNum"),
    @NamedQuery(name = "UserDataEntity.findByUserId", query = "SELECT u FROM UserDataEntity u WHERE u.userId = :userId"),
    @NamedQuery(name = "UserDataEntity.findByDisplayName", query = "SELECT u FROM UserDataEntity u WHERE u.displayName = :displayName"),
    @NamedQuery(name = "UserDataEntity.findByUserPwd", query = "SELECT u FROM UserDataEntity u WHERE u.userPwd = :userPwd")
})
public class UserDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_NUM")
    private Long userNum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "USER_ID")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DISPLAY_NAME")
    private String displayName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USER_PWD")
    private String userPwd;
    
    @Basic(optional = true)
    @Null
    @Transient
    @Column(name = "ROW_CREATE_DTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rowCreateDtm;
    
    @Basic(optional = true)
    @Null
    @Column(name = "LAST_MODIFIED_DTM")
    @Temporal(TemporalType.TIMESTAMP)
    @Transient
    private Date lastModifiedDtm;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userNum")
    private Collection<UserRoleEntity> userRoleEntityCollection;

    public UserDataEntity() {
    }

    public UserDataEntity(Long userNum) {
        this.userNum = userNum;
    }

    public UserDataEntity(Long userNum, String userId, String displayName, String userPwd, Date rowCreateDtm, Date lastModifiedDtm) {
        this.userNum = userNum;
        this.userId = userId;
        this.displayName = displayName;
        this.userPwd = userPwd;
        this.rowCreateDtm = rowCreateDtm;
        this.lastModifiedDtm = lastModifiedDtm;
    }

    public Long getUserNum() {
        return userNum;
    }

    public void setUserNum(Long userNum) {
        this.userNum = userNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Date getRowCreateDtm() {
        return rowCreateDtm;
    }

    public void setRowCreateDtm(Date rowCreateDtm) {
        this.rowCreateDtm = rowCreateDtm;
    }

    public Date getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    public void setLastModifiedDtm(Date lastModifiedDtm) {
        this.lastModifiedDtm = lastModifiedDtm;
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
        hash += (userNum != null ? userNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserDataEntity)) {
            return false;
        }
        UserDataEntity other = (UserDataEntity) object;
        if ((this.userNum == null && other.userNum != null) || (this.userNum != null && !this.userNum.equals(other.userNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserEntity[ userNum=" + userNum + " ]";
    }
    
}
