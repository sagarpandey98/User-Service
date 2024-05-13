package org.example.userservice;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserserviceApplicationTests {
//    @Autowired
//    private JpaRegisteredClientRepository registeredClientRepository;
//
//    @Test
//    void contextLoads() {
//    }
//    @Test
//    @Commit
//    void storeRegisteredClientIntoDb(){
//        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("oidc-client")
//                .clientSecret("{noop}secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/callback")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .scope("ADMIN")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//        registeredClientRepository.save(oidcClient);
//    }

}
