package org.livraria.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.livraria.domain.validation.groups.BuyerGroup;

@Entity
@Table(name = "SystemUsers")
public class SystemUser {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	@NotBlank
	@Column(name = "EMAIl", unique = true)
	private String email;

	@Column(name = "FIRST_NAME")
	@NotBlank(groups = BuyerGroup.class)
	private String name;
	
	@Column(name = "LAST_NAME")
	@NotBlank(groups = BuyerGroup.class)
	private String lastName;
	
	@Column(name = "SOCIAL_ID", unique = true)
	@NotBlank(groups = BuyerGroup.class)
	private String socialId;
	
	@Column(name = "USER_PASSWORD")
	private String password;

	@OneToMany(targetEntity = Address.class, cascade = CascadeType.PERSIST, mappedBy = "systemUser")
	private List<Address> addresses;

	public SystemUser() {
		this.addresses = new ArrayList<>();
	}

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

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "SystemUser [" + (id != null ? "id=" + id + ", " : "") + (email != null ? "email=" + email + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (socialId != null ? "socialId=" + socialId + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (addresses != null ? "addresses=" + addresses : "") + "]";
	}

}
