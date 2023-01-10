package com.jinjin.jintranet.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, length =30 , unique = true)
	private String memberId;
	
	@Column(nullable = false, length =100)
	private String password;
	
	private String name;
	
	private String phoneNo;
	private String mobileNo;

	@Enumerated(EnumType.STRING)
	private PositionType position;
	
	@Enumerated(EnumType.STRING)
	private DepartmentType department;

	private String useColor;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@CreationTimestamp
	private LocalDate sdf;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate edf;

	// 시큐리티 ROLE 용
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@Transient
	private Double total;
	
	@Transient
	private Double use;
	
	@Transient
	private Integer add;
	
	public Member(String memberId, String password, String name, String phoneNo, String mobileNo,
			PositionType position, DepartmentType department, String useColor, RoleType role) {
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.phoneNo = phoneNo;
		this.mobileNo = mobileNo;
		this.position = position;
		this.department = department;
		this.useColor = useColor;
		this.role = role;
	}
	
}
