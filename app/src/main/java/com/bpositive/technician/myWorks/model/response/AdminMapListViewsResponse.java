package com.bpositive.technician.myWorks.model.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AdminMapListViewsResponse implements Serializable {

/*    {
        "status": 1,
            "message": "success all list views mock data",
            "allDevices": [
        {
            "deviceId": "id_camera",
                "deviceName": "Cameras",
                "deviceListViewItem": [
            {
                "deviceId": "id_camera",
                    "deviceLocation": "Building N, Level 3, Spot 25",
                    "deviceStatus": "Bad Network Connectivity",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_camera",
                    "deviceLocation": "Building G, Level 1, Spot 8",
                    "deviceStatus": "Blurred Camera Vision",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_camera",
                    "deviceLocation": "Building L, Level 2, Spot 17",
                    "deviceStatus": "No Communication",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            }
      ]
        },
        {
            "deviceId": "id_lightpoles",
                "deviceName": "Light Poles",
                "deviceListViewItem": [
            {
                "deviceId": "id_lightpoles",
                    "deviceLocation": "Building N, Level 3, Spot 25",
                    "deviceStatus": "Light poles status",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_lightpoles",
                    "deviceLocation": "Building G, Level 1, Spot 8",
                    "deviceStatus": "Light poles status",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_lightpoles",
                    "deviceLocation": "Building L, Level 2, Spot 17",
                    "deviceStatus": "No Communication",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_lightpoles",
                    "deviceLocation": "Building L, Level 3, Spot 17",
                    "deviceStatus": "No Communication",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Setup Repair",
                    "deviceBtnActionType": "Setup Repair",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            }
      ]
        },
        {
            "deviceId": "id_airquality_sensor",
                "deviceName": "Air Quality Sensor",
                "deviceListViewItem": [
            {
                "deviceId": "id_airquality_sensor",
                    "deviceLocation": "Building H, Level 2",
                    "deviceStatus": "Air Quality 50%",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify Citizens",
                    "deviceBtnActionType": "Notify Citizens",
                    "deviceImgUrl": "",
                    "deviceVideoUrl": "",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_airquality_sensor",
                    "deviceLocation": "Building C, Level 2",
                    "deviceStatus": "Air Quality 30% Critical",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify Citizens",
                    "deviceBtnActionType": "Notify Citizens",
                    "deviceImgUrl": "",
                    "deviceVideoUrl": "",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_airquality_sensor",
                    "deviceLocation": "Building H, Level 5",
                    "deviceStatus": "Air Quality 75%",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify Citizens",
                    "deviceBtnActionType": "Notify Citizens",
                    "deviceImgUrl": "",
                    "deviceVideoUrl": "",
                    "deviceMapNavigation": "deviceMapNavigation"
            }
      ]
        },
        {
            "deviceId": "id_environmental_sensor",
                "deviceName": "Environmental Sensor Sensor",
                "deviceListViewItem": [
            {
                "deviceId": "id_environmental_sensor",
                    "deviceLocation": "Building H, Level 2",
                    "deviceStatus": "Environmental 50%",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify",
                    "deviceBtnActionType": "Notify",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_environmental_sensor",
                    "deviceLocation": "Building C, Level 2",
                    "deviceStatus": "Environmental 30% Critical",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify",
                    "deviceBtnActionType": "Notify",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_environmental_sensor",
                    "deviceLocation": "Building H, Level 5",
                    "deviceStatus": "Environmental 75%",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify",
                    "deviceBtnActionType": "Notify",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_environmental_sensor",
                    "deviceLocation": "Building H, Level 1",
                    "deviceStatus": "Environmental 10%",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify",
                    "deviceBtnActionType": "Notify",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            },
            {
                "deviceId": "id_environmental_sensor",
                    "deviceLocation": "Building H, Level 2",
                    "deviceStatus": "Environmental 80%",
                    "deviceStatusInfo": "Kalevantie 2, 33100 Finland",
                    "deviceBtnActionMessage": "Notify",
                    "deviceBtnActionType": "Notify",
                    "deviceImgUrl": "deviceImgUrl",
                    "deviceVideoUrl": "deviceVideoUrl",
                    "deviceMapNavigation": "deviceMapNavigation"
            }
      ]
        }
  ]
    }*/
    public int status;

    public String message;

    @SerializedName("allDevices")
    public ArrayList<AllDevices> allDevicesTabs;

    /**
     * All device list items
     */
    public class AllDevices implements Serializable{

        public String deviceId;
        public String deviceName;

        public ArrayList<DeviceListItem> deviceListViewItem;

        public class DeviceListItem implements Serializable{

            public String deviceId;
            public String deviceLocation;
            public String deviceStatus;
            public String deviceStatusInfo;
            public String deviceBtnActionMessage;
            public String deviceBtnActionType; //  click action should work based on this type
            public String deviceImgUrl;
            public String deviceVideoUrl;
            public String deviceMapNavigation;

        }

    }

}
