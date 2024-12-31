package com.ttisv.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_USER_ROLES", sequenceName = "SEQ_USER_ROLES", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_ROLES")
	private Integer id;

	  @ManyToOne
	    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")
	    private Role role;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}


	

}
