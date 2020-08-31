package com.bpositive.technician.myProfile.model;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest{

	@SerializedName("address")
	private String address;

	@SerializedName("pin_code")
	private String pinCode;

	@SerializedName("device_token")
	private String deviceToken;

	@SerializedName("technician_id")
	private int technicianId;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("device_type")
	private String deviceType;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;
}