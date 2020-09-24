import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { from, Observable } from 'rxjs';
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private oauthService: OAuthService
  ) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return from(this.handleAccess(request, next));
  }

  private handleAccess(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const allowedOrigins = ['http://localhost', 'https://api.videotranscoding.es'];
    if (allowedOrigins.some(url => request.urlWithParams.includes(url))) {
      const accessToken = this.oauthService.authorizationHeader();
      if (accessToken !== undefined) {
        request = request.clone({
          setHeaders: {
            Authorization: accessToken
          }
        });
      }
    }
    return next.handle(request);
  }
}
