import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Queue, Message } from './models'

@Injectable({
  providedIn: 'root'
})
export class QueueManagerServiceService {

  private httpHeaders: HttpHeaders = new HttpHeaders({
    "Authorization": "Basic cXVldWUtbWFuYWdlcjpxdWV1ZU1hbmFnZXJAMTIzNDU=",
    "Content-Type": "application/json",
    "Accept": "application/json"
  });

  private baseUrl: string = "/message-queue-manager/api";

  constructor(private httpClient: HttpClient) { }

  createMessageQueue(queueName: string): Observable<Queue> {
    let headers = this.httpHeaders;
    return this.httpClient.post<Queue>(`${this.baseUrl}/queue/${queueName}`, {}, { headers });
  }

  getMessageQueues(): Observable<Queue[]> {
    let headers = this.httpHeaders;
    return this.httpClient.get<Queue[]>(`${this.baseUrl}/queues`, { headers });
  }

  deleteMessageQueue(queueName: string): Observable<boolean> {
    let headers = this.httpHeaders;
    return this.httpClient.delete<boolean>(`${this.baseUrl}/queue/${queueName}`, { headers });
  }

  publishMessage(message: Message): Observable<Message> {
    let headers = this.httpHeaders;
    return this.httpClient.post<Message>(`${this.baseUrl}/message`, message, { headers });
  }

  getMessages(queueName: string): Observable<Message[]> {
    let headers = this.httpHeaders;
    return this.httpClient.get<Message[]>(`${this.baseUrl}/messages/${queueName}`, { headers });
  }

  getMessagesByPage(queueName: string, page: number, pageSize: number): Observable<Message[]> {
    let headers = this.httpHeaders;
    return this.httpClient.get<Message[]>(`${this.baseUrl}/messages/${queueName}/${page}/${pageSize}`, { headers });
  }

  consumeMessage(queueName: string): Observable<Message> {
    let headers = this.httpHeaders;
    return this.httpClient.delete<Message>(`${this.baseUrl}/message/${queueName}`, { headers });
  }
}
