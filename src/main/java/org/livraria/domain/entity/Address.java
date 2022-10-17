package org.livraria.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Addresses")
public class Address {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(name = "ADDRESS")
	private String addressDesc;
	@NotBlank
	@Column(name = "CITY")
	private String city;
	@NotBlank
	@Column(name = "STATE")
	private String state;
	@NotBlank
	@Column(name = "ZIP_CODE")
	private String zipCode;
	@NotBlank
	@Column(name = "PHONE")
	private String phone;
	
	@ManyToOne(targetEntity = Country.class)
	@JoinColumn(name = "ID_COUNTRY")
	private Country country;
	
	@ManyToOne(targetEntity = SystemUser.class)
	@JoinColumn(name = "ID_SystemUser")
	private SystemUser systemUser;

	/*
	 * Getters and Setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public SystemUser getSystemUser() {
		return systemUser;
	}
	
	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	@Override
	public String toString() {
		return "Address [" + (id != null ? "id=" + id + ", " : "")
				+ (addressDesc != null ? "addressDesc=" + addressDesc + ", " : "")
				+ (city != null ? "city=" + city + ", " : "") + (state != null ? "state=" + state + ", " : "")
				+ (zipCode != null ? "zipCode=" + zipCode + ", " : "") + (phone != null ? "phone=" + phone + ", " : "")
				+ (country != null ? "country=" + country : "") + "]";
	}
	
}
