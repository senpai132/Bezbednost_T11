import { Time } from "@angular/common";

export interface LogEntry{
    id?: number,
    type: string,
    code: string,
    message: string,
    occurrenceDate: Date,
    occurrenceTime: Time
}