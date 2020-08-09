package nl.geospatialAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.geospatialAI.beans.AnswersAdditionalQuestions;
import nl.geospatialAI.beans.SubmitQuestionsAnswersReply;

@Controller

public class SubmitQuestionsAnswersController {

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "/geoSpatialAIserver/submitAnswersAdditionalQuestions")

	@ResponseBody

	public SubmitQuestionsAnswersReply registerRequest(@RequestBody AnswersAdditionalQuestions aRequest) {

		System.out.println("receiving answers for: " + aRequest.getReferenceID());

		SubmitQuestionsAnswersReply stdregreply = new SubmitQuestionsAnswersReply();
		stdregreply.setReferenceID(aRequest.getReferenceID());

		stdregreply.setStatus("OK");

		// We are setting the below value just to reply a message back to the caller

		return stdregreply;

	}

}