import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../_model/page";
import {PageParams} from "../_model/page-params";
import {StudentGroup} from "../_model/student-group";

@Injectable({
  providedIn: 'root'
})
export class StudentGroupService {

  constructor(private _httpClient: HttpClient) {
  }

  getStudentGroupList(sort: string, order: string, page: number, pageSize: number): Observable<Page> {
    const href = '/api/student-group/list';

    return this._httpClient.post<Page>(href, new PageParams(page * pageSize, pageSize, {
      orderBy: sort,
      orderDir: order
    }));
  }

  getStudentGroupByIdd(idd: number): Observable<StudentGroup> {
    const href = '/api/student-group/' + idd;

    return this._httpClient.get<StudentGroup>(href);
  }

  updateStudentGroup(idd: number, data: StudentGroup): Observable<Object> {
    const href = '/api/student-group/' + idd;
    return this._httpClient.patch(href, data);
  }

  createStudentGroup(data: StudentGroup): Observable<Object> {
    const href = '/api/student-group';
    return this._httpClient.post(href, data);
  }

  deleteStudentGroupByIdd(idd: number) {
    const href = '/api/student-group/' + idd;
    return this._httpClient.delete(href);
  }
}
