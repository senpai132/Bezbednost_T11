export interface CertificateRequest {
    id?: bigint,
    commonName: string,
    lastName: string,
    firstName: string,
    organization: string,
    organizationUnit: string,
    country: string,
    email: string,
    locality: string
}
