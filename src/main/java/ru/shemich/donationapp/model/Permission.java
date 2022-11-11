package ru.shemich.donationapp.model;

public enum Permission {
    APP_READ("app:read"),
    APP_WRITE("app:write");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}