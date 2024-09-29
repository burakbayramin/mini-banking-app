package com.burakbayramin.mini_banking_app.constants;

public class PathConstants {

    private PathConstants() {}

    public static final String API = "/api";

    // User Paths
    public static final String USERS = API + "/users";
    public static final String REGISTER = USERS + "/register";
    public static final String LOGIN = USERS + "/login";

    // Account Paths
    public static final String ACCOUNTS = API + "/accounts";
    //public static final String CREATE_ACCOUNT = ACCOUNTS;
    public static final String SEARCH_ACCOUNTS = ACCOUNTS + "/search";
    public static final String ACCOUNT_ID = ACCOUNTS + "/{id}";
//    public static final String UPDATE_ACCOUNT = ACCOUNT_ID;
//    public static final String DELETE_ACCOUNT = ACCOUNT_ID;
//    public static final String VIEW_ACCOUNT_DETAILS = ACCOUNT_ID;

    // Transaction Paths
    public static final String TRANSACTIONS = API + "/transactions";
    public static final String TRANSFER = TRANSACTIONS + "/transfer";
    public static final String TRANSACTION_HISTORY = TRANSACTIONS + "/account/{accountId}";
}

