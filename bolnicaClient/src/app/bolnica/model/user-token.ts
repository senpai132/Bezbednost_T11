export interface UserToken {
    id?: number;
    username: string;
    authorities: string[];
    token: string;
    expireIn: number;
}