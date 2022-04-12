import { IThema } from '@/shared/model/thema.model';

import { Archiv } from '@/shared/model/enumerations/archiv.model';
export interface IBeitrag {
  id?: number;
  content?: string | null;
  attrib?: string | null;
  titel?: string | null;
  rechte?: string | null;
  validFrom?: Date | null;
  validTo?: Date | null;
  publishDate?: Date | null;
  archiv?: Archiv | null;
  thema?: IThema | null;
}

export class Beitrag implements IBeitrag {
  constructor(
    public id?: number,
    public content?: string | null,
    public attrib?: string | null,
    public titel?: string | null,
    public rechte?: string | null,
    public validFrom?: Date | null,
    public validTo?: Date | null,
    public publishDate?: Date | null,
    public archiv?: Archiv | null,
    public thema?: IThema | null
  ) {}
}
