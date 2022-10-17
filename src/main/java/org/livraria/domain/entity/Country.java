package org.livraria.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Countries")
public class Country implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5290748869177615220L;

	@Id
	@Column(name = "ID")
	private Integer id;
	@Column(name = "PHONE_CODE")
	private Integer phoneCode;
	@Column(name = "ISO")
	private String iso;
	@Column(name = "ISO3")
	private String iso3;
	@Column(name = "NAME")
	private String name;
	@Column(name = "FULL_NAME")
	private String fullName;

	/*
	 * Getters and Setters
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getIso3() {
		return iso3;
	}
	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Override
	public String toString() {
		return "Country [" + (id != null ? "id=" + id + ", " : "")
				+ (phoneCode != null ? "phoneCode=" + phoneCode + ", " : "") + (iso != null ? "iso=" + iso + ", " : "")
				+ (iso3 != null ? "iso3=" + iso3 + ", " : "") + (name != null ? "name=" + name + ", " : "")
				+ (fullName != null ? "fullName=" + fullName : "") + "]";
	}
}
