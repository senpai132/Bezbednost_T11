export interface Certificate {
    serialNumber: bigint,
    startDate: Date,
    endDate: Date,
    subjectName: string,
    issuerName: string
}
