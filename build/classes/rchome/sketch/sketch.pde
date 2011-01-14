int i;
int rd[] = {8, 9, 10, 11, 12, 13};
int wr[] = {2, 3, 4, 5, 6, 7};

void setup() {
  for(i = 0; i < 6; i++) {
    pinMode(rd[i], INPUT);
    pinMode(wr[i], OUTPUT);
    digitalWrite(wr[i], LOW);
  }

  Serial.begin(9600);
}

void loop() {
  int recivied;

  if (Serial.available()) {
    recivied = Serial.peek() - 49;
    Serial.flush();

    switch(recivied) {
      case 50:
        change(read());
        break;
      case 55:
        high(read());
        break;
      case 59:
        low(read());
        break;
      case 63:
        print(read());
        break;
    }
  }
}

void change(int recivied) {
  if (digitalRead(rd[recivied]) == HIGH)
    digitalWrite(wr[recivied], LOW);
  else
    digitalWrite(wr[recivied], HIGH);
}

void high(int recivied) {
  digitalWrite(wr[recivied], HIGH);
}

void low(int recivied) {
  digitalWrite(wr[recivied], LOW);
}

void print(int recivied) {
  Serial.println(digitalRead(rd[recivied]), DEC);
}

int read() {
  int recivied;

  while(!Serial.available())
    i++;
  recivied = Serial.peek() - 49;
  Serial.flush();

  return recivied;
}

