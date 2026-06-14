CREATE TABLE IF NOT EXISTS t_drone (
  id   INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
  name TEXT         NOT NULL,
  model TEXT,
  serial_no TEXT,
  attributes_json TEXT,
  remark   TEXT,
  created_at  TEXT   NOT NULL,
  updated_at  TEXT   NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS uk_drone_serial ON t_drone (serial_no);
