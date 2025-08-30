package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Когда GET /api/rates вызван, сервис возвращает список валют и статус 200 OK"
    request {
        method 'GET'
        url '/api/rates'
        headers {
            accept(applicationJson())
        }
    }
    response {
        status 200
        headers { contentType(applicationJson()) }
        body([
                [ title: 'Белорусский Рубль', rate: 123.45, value: 'BYN' ],
                [ title: 'Российский Рубль',  rate: 98.76, value: 'RUB' ]
        ])
        bodyMatchers {
            jsonPath('$[*].title', byRegex('[\\p{L}\\s-]+'))
            jsonPath('$[*].rate',  byRegex('\\d+(\\.\\d+)?'))
            jsonPath('$[*].value', byRegex('(RUB|BYN|IRR|CNY|INR)'))
        }
    }
}