package com.dong.system.utils;

import java.io.Serializable;
import java.util.List;

import com.dong.system.domain.SysUser;
import lombok.Data;

@Data

public class ActiverUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SysUser sysUser;
	private List<String> roles;
	private List<String> permissions;

}
