/* rchome.arduino.sketch.pde */
/*
 * RCHome - For more modern homes
 * 
 * Copyright (C) 2011 Mônica Nelly  <monica.araujo@itec.ufpa.br>
 * Copyright (C) 2011 Willian Paixao <willian@ufpa.br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Source code for ATMEGA328p
 * 
 * @author Mônica Nelly   <monica.araujo@itec.ufpa.br>
 * @author Willian Paixao <willian@ufpa.br>
 * @version 0.001
 */
 
int i;
/**
 * wr[i] Write on pins HIGH or LOW constants.
 * 		 Each element of this vector is a port number.
 * 
 * rd[i] Have the same value than wr[i].
 * 		 Thus, each element listen one port.
 */
int rd[] = {LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW, LOW};
int wr[] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, A0, A1, A2, A3, A4, A5};

/**
 * Configuring the pins for OUTPUT and put default values on each port.
 */
void setup() {
  for(i = 0; i < 17; i++) {
    pinMode(wr[i], OUTPUT);
    digitalWrite(wr[i], LOW);
  }

  Serial.begin(9600);
}

void loop() {
  int recivied;
  /**
   * Checks for data avaibility
   */
  if (Serial.available()) {
    /**
     * The recivied data are subtracted by 49.
     * This way, <code>recivied</code> can be used like a
     * vector index.
     */ 
    recivied = Serial.peek() - 49;
    Serial.flush();

    switch(recivied) {
      case 50:
      /* 50 = 99(c character) - 49(subtracted above) */
        change(read());
        break;
      case 55:
      /* 55 = 104(h character) - 49(subtracted above) */
        high(read());
        break;
      case 59:
      /* 59 = 108(l character) - 49(subtracted above) */
        low(read());
        break;
      case 63:
      /* 63 = 112(p character) - 49(subtracted above) */
        print(read());
        break;
    }
  }
}

/**
 * Change the output of pin.
 * If a pin is in HIGH, changes to LOW and vice versa.
 */
void change(int recivied) {
  if (rd[recivied] == HIGH) {
    rd[recivied] = LOW;
    digitalWrite(wr[recivied], LOW);
  }
  else {
    rd[recivied] = HIGH;
    digitalWrite(wr[recivied], HIGH);
  }
}

void high(int recivied) {
  rd[recivied] = HIGH;
  digitalWrite(wr[recivied], HIGH);
}

void low(int recivied) {
  rd[recivied] = LOW;
  digitalWrite(wr[recivied], LOW);
}

void print(int recivied) {
  Serial.println(rd[recivied], DEC);
}

int read() {
  int recivied;

  while(!Serial.available())
    continue;
  recivied = Serial.peek() - 49;
  Serial.flush();

  return recivied;
}
