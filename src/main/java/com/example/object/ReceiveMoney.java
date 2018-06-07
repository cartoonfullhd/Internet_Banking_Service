package com.example.object;


import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class ReceiveMoney implements JsonNonNullVoInterface
{
	private String SendBankCode;
	private String SendAccountID;
	private String Amount;
}
