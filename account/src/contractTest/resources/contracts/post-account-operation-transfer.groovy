package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Когда POST /account/{login}/operation/transfer вызван, сервис выполняет операцию перевода денег на другой счёт, 200 OK"
    request {
        method 'POST'
        urlPath($(consumer(regex('/account/[A-Za-z0-9._-]+/operation/transfer')), producer('/account/username/operation/transfer')))
        headers {
            contentType(applicationJson())
        }
        body(
                targetLogin: $(consumer(regex('[A-Za-z0-9._-]+')),               producer('targetUsername')),
                amount:      $(consumer(regex('^(?:0|[1-9]\\d*)(?:\\.\\d+)?$')), producer(123.45))
        )
    }
    response {
        status 200
    }
}