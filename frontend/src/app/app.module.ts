import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HeaderComponent } from './componants/header/header.component';
import { FooterComponent } from './componants/footer/footer.component';
import { SignUpComponent } from './componants/sign-up/sign-up.component';
import { ContactComponent } from './componants/contact/contact.component';
import { UserhomeComponent } from './componants/userhome/userhome.component';
import { LeftBarComponent } from './componants/userhome/left-bar/left-bar.component';
import { RightBarComponent } from './componants/userhome/right-bar/right-bar.component';
import { PublishComponent } from './componants/userhome/publish/publish.component';
import { FriendsComponent } from './componants/userhome/friends/friends.component';
import { CoolImagesComponent } from './componants/userhome/cool-images/cool-images.component';
import { MainPageComponent } from './componants/userhome/main-page/main-page.component';
import { TimeLinesComponent } from './componants/time-lines/time-lines.component';
import { TimeLineComponent } from './componants/time-lines/time-line/time-line.component';
import { TimeAboutComponent } from './componants/time-lines/time-about/time-about.component';
import { TimeAlbumComponent } from './componants/time-lines/time-album/time-album.component';
import { TimeFriendsComponent } from './componants/time-lines/time-friends/time-friends.component';
import { TimeLineProfileComponent } from './componants/time-lines/time-line-profile/time-line-profile.component';
import { TimeLineDetailesComponent } from './componants/time-lines/time-line-detailes/time-line-detailes.component';
import {RouterModule, Routes} from '@angular/router';
import { LoginComponent } from './componants/login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {AuthGuard} from '../services/guard/auth.guard';
import {AuthInterceptor} from '../services/interceptor/auth.interceptor';
import {GuestGuard} from '../services/guard/guest.guard';
import {CommonModule, NgIf} from '@angular/common';
import { FeaturesComponent } from './componants/login/features/features.component';


// http://localhost:4200

const routes: Routes = [
  {path: 'sigin-up', component: SignUpComponent, canActivate: [GuestGuard]},
  {path: 'login', component: LoginComponent, canActivate: [GuestGuard]},
  {path: 'features', component: FeaturesComponent, canActivate: [GuestGuard]},
  {path: 'mainpage', component: MainPageComponent, canActivate: [AuthGuard]},
  {path: 'contact', component: ContactComponent},
  {path: 'timeline', component: TimeLineComponent, canActivate: [AuthGuard]},
  {path: 'timeline-about', component: TimeAboutComponent, canActivate: [AuthGuard]},
  {path: 'timeline-friends', component: TimeFriendsComponent, canActivate: [AuthGuard]},
  {path: 'timeline-album', component: TimeAlbumComponent, canActivate: [AuthGuard]},
  {path: '', component: MainPageComponent, canActivate: [AuthGuard]},
  {path: '**', component: MainPageComponent, canActivate: [AuthGuard]},
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SignUpComponent,
    ContactComponent,
    UserhomeComponent,
    LeftBarComponent,
    RightBarComponent,
    PublishComponent,
    FriendsComponent,
    CoolImagesComponent,
    MainPageComponent,
    TimeLinesComponent,
    TimeLineComponent,
    TimeAboutComponent,
    TimeAlbumComponent,
    TimeFriendsComponent,
    TimeLineProfileComponent,
    TimeLineDetailesComponent,
    LoginComponent,
    FeaturesComponent,

  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule,
    CommonModule,
  ],
  providers: [  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
