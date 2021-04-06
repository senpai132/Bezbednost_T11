import { Icons } from 'src/app/enums/icons.enum';

export interface TableOperation<T> {
    operation: (element: T) => void;
    icon: Icons;
}