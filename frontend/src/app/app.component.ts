import { environment } from './../environments/environment';
import { OAuthService, JwksValidationHandler, AuthConfig } from 'angular-oauth2-oidc';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

export const authConfig: AuthConfig = {
    issuer: environment.issuer ,
    redirectUri: window.location.origin,
    clientId: environment.client ,
    scope: 'openid profile',
    showDebugInformation: false,
    responseType: 'code',
    requireHttps: false
};

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AppComponent {
    constructor(private oauthService: OAuthService) {
        this.oauthService.configure(authConfig);
        this.oauthService.loadDiscoveryDocumentAndTryLogin();

    }
}
