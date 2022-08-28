//package com.example.springboottest;
//
//import com.azure.identity.ClientSecretCredential;
//import com.azure.identity.ClientSecretCredentialBuilder;
//import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
//import com.microsoft.graph.requests.GraphServiceClient;
//import okhttp3.Request;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//class SpringboottestApplicationTests {
//    public void contextLoads() {
//        String clientId = "28209641-99f2-4109-bc98-20781be7ebe8";
//        String clientSecret = "85P8Q~rg-YEk1~DyLKzk31mZsEAMquByJMOAEbPd";
//        String tenantId = "0f0fdd9f-01bc-46e3-b247-cc720ea5a920";
//        List<String> scopes = new ArrayList<>();
//        scopes.add("api://28209641-99f2-4109-bc98-20781be7ebe8/.default");
//        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .tenantId(tenantId)
//                .build();
//
//        TokenCredentialAuthProvider tokenCredentialAuthProvider =
//                new TokenCredentialAuthProvider(scopes, clientSecretCredential);
//
//        GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
//                .authenticationProvider(tokenCredentialAuthProvider)
//                .buildClient();
//    }
//
//    public static void main(String[] args) {
//        String clientId = "28209641-99f2-4109-bc98-20781be7ebe8";
//        String clientSecret = "85P8Q~rg-YEk1~DyLKzk31mZsEAMquByJMOAEbPd";
//        String tenantId = "0f0fdd9f-01bc-46e3-b247-cc720ea5a920";
//        List<String> scopes = new ArrayList<>();
//        scopes.add("api://28209641-99f2-4109-bc98-20781be7ebe8/.default");
//
//        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .tenantId(tenantId)
//                .build();
//        TokenCredentialAuthProvider tokenCredentialAuthProvider =
//                new TokenCredentialAuthProvider(scopes, clientSecretCredential);
//        GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
//                .authenticationProvider(tokenCredentialAuthProvider)
//                .buildClient();
//        System.out.println(graphClient);
//    }
//
//}
