import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {User} from "../_model/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _httpClient: HttpClient) {
  }

  getUserList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/user/list';

    return this._httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy: sort,
      orderDir: order
    }));
  }

  getUserByIdd(idd: number): Observable<User> {
    const href = '/api/user/' + idd;

    return this._httpClient.get<User>(href);
  }

  updateUser(idd: number, data: User): Observable<Object> {
    const href = '/api/user/' + idd;
    return this._httpClient.patch(href, data);
  }

  createUser(data: User): Observable<Object> {
    const href = '/api/user';
    return this._httpClient.post(href, data);
  }

  deleteUserByIdd(idd: number) {
    const href = '/api/user/' + idd;
    return this._httpClient.delete(href);
  }
}
