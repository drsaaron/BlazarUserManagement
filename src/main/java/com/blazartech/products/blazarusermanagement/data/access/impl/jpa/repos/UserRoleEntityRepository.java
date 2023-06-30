/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos;

import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleEntity;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AAR1069
 */
@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {
    
    public Collection<UserRoleEntity> findByUserNum(Long userNum);
}
