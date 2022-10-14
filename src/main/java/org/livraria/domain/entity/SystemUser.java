package org.livraria.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "SystemUsers")
public class SystemUser {
	
	public SystemUser() {
		this.addresses = new ArrayList<Address>();
	}
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Email
	@Column(name = "EMAIl")
	private String email;
	@NotBlank
	@Column(name = "FIRST_NAME")
	private String name;
	@NotBlank
	@Column(name = "LAST_NAME")
	private String lastName;
	@NotBlank
	@Column(name = "SOCIAL_ID")
	private String socialId;
	@Column(name = "USER_PASSWORD")
	private String password;

	@OneToMany(targetEntity = Address.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "Addresses", joinColumns = { @JoinColumn(name = "ID_SystemUser") }, inverseJoinColumns = {
			@JoinColumn(name = "ID") })
	private List<Address> addresses;

	/*
	 * Getters and Setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}
}
