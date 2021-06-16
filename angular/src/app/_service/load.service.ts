import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {Load} from "../_model/load";

@Injectable({
  providedIn: 'root'
})
export class LoadService {

  constructor(private _httpClient: HttpClient) {
  }

  getLoadList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/load/list';

    var data = this._httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy: sort,
      orderDir: order
    }));
    return data
  }

  getLoadByIdd(idd: number): Observable<Load> {
    const href = '/api/load/' + idd;

    return this._httpClient.get<Load>(href);
  }

  updateLoad(idd: number, data: Load): Observable<Object> {
    const href = '/api/load/' + idd;
    return this._httpClient.patch(href, data);
  }

  createLoad(data: Load): Observable<Object> {
    const href = '/api/load';
    return this._httpClient.post(href, data);
  }

  deleteLoadByIdd(idd: number) {
    const href = '/api/load/' + idd;
    return this._httpClient.delete(href);
  }
}
