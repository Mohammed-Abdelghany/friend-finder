import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../../services/auth.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})

export class LoginComponent implements OnInit {
  username = '';
  password = '';
  error = '';

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params.username) {
        this.username = params.username;
      }
    });
  }

  Login(): void {
    this.authService.login({username: this.username, password: this.password}).subscribe(
    // tslint:disable-next-line:no-debugger
      response => {
        console.log('Login successful', response);
        Swal.fire(`Welcome ${this.username} 👌`, ' ', 'success');
        this.router.navigate(['/']).then(r => {
        });
      }
      ,
      err => {
        Swal.fire('Invalid email or password ❌', 'please try again', 'error');
      }
    );
  }



}


