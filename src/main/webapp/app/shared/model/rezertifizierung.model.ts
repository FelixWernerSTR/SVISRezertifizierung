export interface IRezertifizierung {
  id?: number;
  loginName?: string | null;
  nachname?: string | null;
  vorname?: string | null;
  vermittlerNummer?: number;
  rollenZugehoerigkeiten?: string | null;
  dvcVertreterNummer?: string | null;
  dvcBenutzerGruppe?: string | null;
  icisSrGebaude?: string | null;
  icisSrHaftpflicht?: string | null;
  icisSrLeitungswasser?: string | null;
  icisSrKfzKasko?: string | null;
}

export class Rezertifizierung implements IRezertifizierung {
  constructor(
    public id?: number,
    public loginName?: string | null,
    public nachname?: string | null,
    public vorname?: string | null,
    public vermittlerNummer?: number,
    public rollenZugehoerigkeiten?: string | null,
    public dvcVertreterNummer?: string | null,
    public dvcBenutzerGruppe?: string | null,
    public icisSrGebaude?: string | null,
    public icisSrHaftpflicht?: string | null,
    public icisSrLeitungswasser?: string | null,
    public icisSrKfzKasko?: string | null
  ) {}
}
