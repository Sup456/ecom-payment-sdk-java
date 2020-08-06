## Ecom Payment API Java SDK

#### Документация

**API**: [https://e-commerce.raiffeisen.ru/api/doc/ecom.html](Документация)

#### Использование

Для использования SDK необходимо создать объект класса `EcomClient`, указав в конструкторе домен, на который будут отправляться запросы (`EcomClient.PRODUCTION_DOMAIN` или `EcomClient.TEST_DOMAIN`), и секретный ключ для авторизации:

 ~~~ java
String secretKey = "...";

EcomClient client = new EcomClient(EcomClient.PRODUCTION_DOMAIN, secretKey);
 ~~~

Все запросы осуществляются классаом `EcomClient` и возвращают объекты следующих классов:
- `OrderInfo` для информации о статусе заказа
- `RefundInfo` для возвратов

Эти классы содержат в себе те же поля, что и ответ сервера.

В случае логической ошибки клиент вернёт исключение `EcomException` с сообщением об ошибке.

#### Получение информации о статусе заказа

Для получения информации о статусе заказа необходимо создать экземпляр класса `OrderId`, передав в конструктор id заказа. После этого следует вызвать у клиента метод `getOrderInfo(OrderId)`, передав экземпляр `OrderId`:

~~~ java
String ORDER_ID = "...";

OrderId orderId = new OrderId(ORDER_ID);

OrderInfo orderInfo = client.getOrderInfo(orderId);
~~~

Ответ:

~~~
{
  "code": "SUCCESS",
  "transaction": {
    "id": 120059,
    "orderId": "testOrder",
    "status": {
      "value": "SUCCESS",
      "date": "2019-07-11T17:45:13+03:00"
    },
    "paymentMethod": "acquiring",
    "paymentParams": {
      "rrn": 935014591810,
      "authCode": "025984"
    },
    "amount": 12500.5,
    "comment": "Покупка шоколадного торта",
    "extra": {
      "additionalInfo": "Sweet Cake"
    }
  }
}
~~~

#### Возврат средств

Необходимо создать объект класса `RefundRequest`, передав в конструкторе id заказа, id возврата, сумму возврата в рублях, и вызвать метод `requestRefund(RefundRequest)`:

~~~ java
String ORDER_ID = "...";

BigDecimal AMOUNT = BigDecimal.valueOf(0.01);

RefundRequest request = RefundRequest.creator().
         orderId(ORDER_ID).
         refundId(getRefundId()).
         amount(AMOUNT).create();

RefundInfo refund = client.requestRefund(request);
~~~

Ответ

~~~
{
  "code": "SUCCESS",
  "amount": 150,
  "refundStatus": "COMPLETED"
}
~~~

#### Получение информации о статусе возврата

Необходимо создать объект класса `RefundRequest`, передав в конструкторе id заказа, id возврата, и вызвать метод `getRefundInfo(RefundRequest)`:

~~~ java
String ORDER_ID = "...";

RefundRequest request = RefundRequest.creator().
         orderId(ORDER_ID).
         refundId(getRefundId()).
         create();

RefundInfo refund = client.getRefundInfo(request);
~~~

Ответ:

~~~
{
  "code": "SUCCESS",
  "amount": 150,
  "refundStatus": "COMPLETED"
}
~~~

#### Обработка уведомлений

Для обработки уведомлений существует класс `PaymentNotification`. Инициализация происходит с помощью статического метода `PaymentNotification.fromJson(String)`:

~~~ java
String jsonString = "...";
PaymentNotification notification = PaymentNotification.fromJson(jsonString);
~~~

#### Проверка подписи

Для проверки подлинности уведомления существует класс `EcomUtils`. Проверка подписи осуществляется при помощи перегруженного статического метода `checkNotificationSignature`. Примеры использования:

~~~ java
String jsonString = "..."; // json-уведомление, приведённое в строку
String publicId = "...";
String apiSignature = "...";
String secretKey = "...";

boolean success = EcomUtils.checkNotificationSignature(jsonString, publicId, apiSignature, secretKey);
~~~

~~~ java
PaymentNotification notification = ...;
String publicId = "...";
String apiSignature = "...";
String secretKey = "...";

boolean success = EcomUtils.checkNotificationSignature(notification, publicId, apiSignature, secretKey);
~~~

~~~ java
BigDecimal amount = ...;
String publicId = "...";
String orderId = "...";
String transactionStatusValue = "...";
String transactionStatusDate = "...";

String apiSignature = "...";
String secretKey = "...";

boolean success = SbpUtils.checkNotificationSignature(amount, 
                 	publicId, 
                 	orderId,
                 	transactionStatusValue,
                 	transactionStatusDate,
                 	apiSignature,
                 	secretKey);
~~~

#### Использование альтернативного HTTP-клиента

По умолчанию для HTTP-запросов используется Apache (класс `ApacheWebClient`), но можно воспользоваться любым другим, реализовав интерфейс `WebClient`:

~~~ java
public interface WebClient extends Closeable {
    Response request(String method, 
			String url, 
			Map<String, String> headers, 
			String entity) throws IOException;
}
~~~

Примеры использования:

~~~ java
CustomWebClient customClient = ...;
EcomClient client = new EcomClient(SbpClient.PRODUCTION_DOMAIN, secretKey, customClient); 
~~~

~~~ java
EcomClient client = ...;

...

CustomWebClient customClient = ...;
client.setWebClient(customClient);
~~~















